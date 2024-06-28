import csv
import random
from functools import cache
from urllib.parse import urlencode

from locust import task, run_single_user
from locust import FastHttpUser
from locust.exception import LocustError

USER_AGENT = "eduID Locust Test"
ACCEPT_HTML = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
ACCEPT_JSON = "application/json"
ACCEPT_ENCODING = "gzip, deflate, br"
CONTENT_TYPE_JSON = "application/json; charset=utf-8"


JSCONFIG_PATH = "/services/jsconfig/config"


@cache
def read_users(filename: str, delimiter: str = ",", quotechar: str = "|"):
    with open(filename, newline="") as csvfile:
        reader = csv.reader(csvfile, delimiter=delimiter, quotechar=quotechar)
        return [row for row in reader]


class eduid_login(FastHttpUser):
    host = "https://dev.eduid.se"
    default_headers = {"accept-language": "sv,de;q=0.9,en-US;q=0.8,en;q=0.7"}
    if host == "https://html.eduid.docker":
        users = read_users("../users.local.csv")
        referer = "https://html.eduid.docker/"
        dashboard_host = "dashboard.eduid.docker"
        idp_host = "idp.eduid.docker"
    elif host == "https://dev.eduid.se":
        users = read_users("../users.staging.csv")
        referer = "https://dev.eduid.se/"
        dashboard_host = "dashboard.dev.eduid.se"
        idp_host = "idp.dev.eduid.se"
    else:
        raise LocustError(f"No config defined for this host, {host}")

    idp_next = f"https://{idp_host}/services/idp/next"

    @task
    def t(self):
        with self.client.request(
            "GET",
            "/",
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "user-agent": USER_AGENT,
            },
            catch_response=True,
        ) as resp:
            if (
                "You need to enable JavaScript to run this app."
                not in resp.content.decode("utf-8")
            ):
                resp.failure(
                    f"'You need to enable JavaScript to run this app.' not found in response: {resp.content.decode('utf-8')}"
                )
        with self.client.request(
            method="GET",
            url=JSCONFIG_PATH,
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
        ) as resp:
            csrf_token = resp.json().get("payload", {}).get("csrf_token")
            if csrf_token is None:
                resp.failure("Got no csrf token")
        with self.client.request(
            "GET",
            f"https://{self.dashboard_host}/services/personal-data/all-user-data",
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "content-type": CONTENT_TYPE_JSON,
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
            catch_response=True,
            allow_redirects=False,
        ) as resp:
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("Got no redirect location 1")
        with self.client.request(
            "GET",
            redirect_location,
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "content-type": CONTENT_TYPE_JSON,
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
            catch_response=True,
            allow_redirects=False,
        ) as resp:
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("Got no redirect location 2")
        with self.client.request(
            "GET",
            redirect_location,
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "referer": self.referer,
                "user-agent": USER_AGENT,
            },
            catch_response=True,
            allow_redirects=False,
        ) as resp:
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("Got no redirect location 3")
        with self.client.request(
            "GET",
            redirect_location,
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "referer": self.referer,
                "user-agent": USER_AGENT,
            },
            catch_response=True,
        ) as resp:
            login_ref = resp.url.split("/")[-1]
            if not login_ref:
                resp.failure("Got no login ref")
        with self.client.request(
            "POST",
            self.idp_next,
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "content-type": CONTENT_TYPE_JSON,
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
            json={"ref": login_ref, "remember_me": False, "csrf_token": csrf_token},
            catch_response=True,
        ) as resp:
            if resp.json().get("payload", {}).get("action") != "USERNAMEPASSWORD":
                resp.failure(f"Got unexpected action: {resp.json().get('payload', {}).get('action')}")
        user = random.choice(self.users)
        with self.client.request(
            "POST",
            f"https://{self.idp_host}/services/idp/pw_auth",
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "content-type": CONTENT_TYPE_JSON,
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
            json={
                "ref": login_ref,
                "remember_me": False,
                "username": user[0],
                "password": user[1],
                "csrf_token": csrf_token,
            },
            catch_response=True,
        ) as resp:
            if resp.json()["type"] != "POST_PW_AUTH_PW_AUTH_SUCCESS":
                resp.failure(f"Got unexpected response type: {resp.json()['type']}")
        with self.client.request(
            "POST",
            self.idp_next,
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "content-type": CONTENT_TYPE_JSON,
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
            json={
                "ref": login_ref,
                "remember_me": False,
                "csrf_token": csrf_token,
            },
            catch_response=True,
        ) as resp:
            if resp.json().get("payload", {}).get("action") != "FINISHED":
                resp.failure(f"Got unexpected action: {resp.json().get('payload', {}).get('action')}")
            saml_target = resp.json().get("payload", {}).get("target")
            saml_parameters = resp.json().get("payload", {}).get("parameters")
            if not saml_target or not saml_parameters:
                resp.failure("Got no SAML target or parameters")
            if saml_parameters["used"] is False:
                del saml_parameters["used"]
        with self.client.request(
            "POST",
            saml_target,
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "content-type": "application/x-www-form-urlencoded",
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
            },
            data=urlencode(saml_parameters),
            catch_response=True,
            allow_redirects=False,
        ) as resp:
            assert resp.status_code == 302
            if resp.headers["location"] != f"https://{self.dashboard_host}/services/personal-data/all-user-data":
                resp.failure(f"{resp.headers['location']} != https://{self.dashboard_host}/services/personal-data/all-user-data")
        with self.client.request(
            "GET",
            f"https://{self.dashboard_host}/services/personal-data/all-user-data",
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
        ) as resp:
            if resp.json().get("type") != "GET_PERSONAL_DATA_ALL_USER_DATA_SUCCESS":
                resp.failure(
                    f"{resp.json().get('type')} != 'GET_PERSONAL_DATA_ALL_USER_DATA_SUCCESS'"
                )

        # clear cookies to no longer be logged in
        self.client.cookiejar.clear()


if __name__ == "__main__":
    run_single_user(eduid_login)
