import csv
import random
import logging
from functools import cache
from html.parser import HTMLParser
from json import JSONDecodeError
from typing import Optional
from urllib.parse import urlencode

from locust import task, run_single_user
from locust import FastHttpUser
from locust.exception import LocustError

logger = logging.getLogger(__name__)

USER_AGENT = "eduID Locust Test"
ACCEPT_HTML = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
ACCEPT_JSON = "application/json"
ACCEPT_ENCODING = "gzip, deflate, br"
CONTENT_TYPE_JSON = "application/json; charset=utf-8"


@cache
def read_users(filename: str, delimiter: str = ",", quotechar: str = "|"):
    with open(filename, newline="") as csvfile:
        reader = csv.reader(csvfile, delimiter=delimiter, quotechar=quotechar)
        return [row for row in reader]


class SAMLFormParser(HTMLParser):
    def __init__(self):
        super().__init__()
        self.acs_url: Optional[str] = None
        self.saml_response: Optional[str] = None
        self.relay_state: Optional[str] = None

    def handle_starttag(self, tag, attrs):
        if tag == "form":
            match dict(attrs):
                case {"action": acs_url, "method": "post"}:
                    self.acs_url = acs_url
        if tag == "input":
            match dict(attrs):
                case {"type": "hidden", "name": "SAMLResponse", "value": value}:
                    self.saml_response = value
                case {"type": "hidden", "name": "RelayState", "value": value}:
                    self.relay_state = value

    def get_saml_parameters(self) -> dict[str, str]:
        return {
            "SAMLResponse": self.saml_response,
            "RelayState": self.relay_state,
        }


class ManagedAccountLogin(FastHttpUser):
    host = "https://fidustest.skolverket.se"
    default_headers = {"accept-language": "sv,de;q=0.9,en-US;q=0.8,en;q=0.7"}
    users = read_users("../users.managed.csv")
    referer = "https://dev.eduid.se/"
    jsconfig_path = "https://dev.eduid.se/services/jsconfig/config"
    idp_host = "idp.dev.eduid.se"
    idp_next = f"https://{idp_host}/services/idp/next"

    saml_form = SAMLFormParser()

    @task
    def t(self):
        # clear cookies to no longer be logged in
        self.client.cookiejar.clear()
        user = random.choice(self.users)
        logger.info(f"Running task with user: {user[0]}")
        with self.client.request(
            "GET",
            "/Shibboleth.sso/Login?target=https://fidustest.skolverket.se/secure&entityID=https://idp-skolverket.dev.eduid.se/2021005208",
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "user-agent": USER_AGENT,
            },
            catch_response=True,
            allow_redirects=False,
        ) as resp:
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("Got no redirect location 1")
            logger.debug(f"User {user[0]} completed {resp.request.url}")
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
            name="https://idp-skolverket.dev.eduid.se/EduidSP/2021005208/sso/redirect?SAMLRequest={saml_request}&RelayState={relay_state}"
        ) as resp:
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("Got no redirect location 2")
            logger.debug(f"User {user[0]} completed {resp.request.url}")
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
            name="https://idp.dev.eduid.se/sso/redirect?SAMLRequest={saml_request}&RelayState={relay_state}"
        ) as resp:
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("Got no redirect location 3")
            logger.debug(f"User {user[0]} completed {resp.request.url}")
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
            name="https://dev.eduid.se/login/{login_ref}"
        ) as resp:
            login_ref = resp.url.split("/")[-1]
            if not login_ref:
                resp.failure("Got no login ref")
            logger.debug(f"User {user[0]} completed {resp.request.url}")
        with self.client.request(
            method="GET",
            url=self.jsconfig_path,
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
            logger.debug(f"User {user[0]} completed {resp.request.url}")
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
                resp.failure(
                    f"Got unexpected action: {resp.json().get('payload', {}).get('action')}"
                )
            logger.debug(f"User {user[0]} completed {resp.request.url}")
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
            try:
                json_data = resp.json()
            except JSONDecodeError:
                resp.failure(f"{resp.content} is not valid JSON")
            else:
                if json_data.get("type") != "POST_PW_AUTH_PW_AUTH_SUCCESS":
                    resp.failure(f"Got unexpected response type: {resp.json()['type']}")
            logger.debug(f"User {user[0]} completed {resp.request.url}")
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
                resp.failure(
                    f"Got unexpected action: {resp.json().get('payload', {}).get('action')}"
                )
            saml_target = resp.json().get("payload", {}).get("target")
            saml_parameters = resp.json().get("payload", {}).get("parameters")
            if not saml_target or not saml_parameters:
                resp.failure("Got no SAML target or parameters")
            if saml_parameters["used"] is False:
                del saml_parameters["used"]
            logger.debug(f"User {user[0]} completed {resp.request.url}")
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
            assert resp.status_code == 200
            self.saml_form.feed(resp.content.decode("utf-8"))
            if (
                self.saml_form.saml_response is None
                or self.saml_form.relay_state is None
                or self.saml_form.acs_url is None
            ):
                resp.failure(
                    "Could not find SAMLResponse, RelayState or ACS URL in SAML response"
                )
            logger.debug(f"User {user[0]} completed {resp.request.url}")
        with self.client.request(
            "POST",
            self.saml_form.acs_url,
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "content-type": "application/x-www-form-urlencoded",
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
            },
            data=urlencode(self.saml_form.get_saml_parameters()),
            catch_response=True,
            allow_redirects=False,
        ) as resp:
            assert resp.status_code == 302
            redirect_location = resp.headers["location"]
            if redirect_location != "https://fidustest.skolverket.se/secure":
                resp.failure(
                    f"{redirect_location} != https://fidustest.skolverket.se/secure"
                )
            logger.debug(f"User {user[0]} completed {resp.request.url}")
        with self.client.request(
            "GET",
            redirect_location,
            headers={
                "accept": ACCEPT_JSON,
                "accept-encoding": ACCEPT_ENCODING,
                "origin": self.host,
                "referer": self.referer,
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
        ) as resp:
            text = resp.content.decode("utf-8")
            if "Du har nu lyckats logga in till testsidan." not in text:
                resp.failure("Missing text in response")
            logger.debug(f"User {user[0]} completed {resp.request.url}")


if __name__ == "__main__":
    run_single_user(ManagedAccountLogin)
