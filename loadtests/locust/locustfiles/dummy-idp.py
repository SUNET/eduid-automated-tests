import logging
from html.parser import HTMLParser
from typing import Optional
from urllib.parse import urlencode
import re

from geventhttpclient.useragent import CompatResponse
from locust import task, run_single_user
from locust import FastHttpUser
from locust.clients import ResponseContextManager
from locust.exception import RescheduleTask

logger = logging.getLogger(__name__)

USER_AGENT = "eduID Locust Test"
ACCEPT_HTML = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
ACCEPT_JSON = "application/json"
ACCEPT_ENCODING = "gzip, deflate, br"
CONTENT_TYPE_JSON = "application/json; charset=utf-8"


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


class fidustest_skolverket_se(FastHttpUser):
    host = "https://fidustest.skolverket.se"
    default_headers = {"accept-language": "sv,de;q=0.9,en-US;q=0.8,en;q=0.7"}
    referer = "https://locustest.dev/"
    network_timeout = 600.0
    connection_timeout = 600.0

    def _fail_and_log_resp(
        self,
        resp: ResponseContextManager | CompatResponse,
        request_id: str,
        message: Optional[str] = None,
    ):
        logger.error(f"{request_id}: Code = {resp.status_code} from {resp.request.url}")
        logger.error(f"{request_id}: Resp = {resp.text}")
        if message:
            resp.failure(f"{request_id}: {message}")
        else:
            resp.failure(f"{request_id}: Response code {resp.status_code}")
        raise RescheduleTask()

    @task
    def t(self):
        with self.client.request(
            "GET",
            "/Shibboleth.sso/Login?target=https://fidustest.skolverket.se/secure/DNP.php&entityID=https://idpproxy.dev.eduid.se/idp",
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "user-agent": USER_AGENT,
            },
            catch_response=True,
            allow_redirects=False,
            name="1:fidustest.skolverket.se"[:60],
        ) as resp:
            if not resp.ok:
                self._fail_and_log_resp(resp, request_id="1")
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("1: Got no redirect location 1")
            logger.debug(f"1: Completed {resp.request.url}")
            # logger.debug(f"Location = {redirect_location}")
        with self.client.request(
            "GET",
            redirect_location,
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "referer": "https://fidustest.skolverket.se/",
                "user-agent": USER_AGENT,
            },
            catch_response=True,
            allow_redirects=False,
            name="2:https://idpproxy.dev.eduid.se/Saml2SP/sso/redirect?SAMLRequest={saml_request}&RelayState={relay_state}"[
                :60
            ],
        ) as resp:
            if not resp.ok:
                self._fail_and_log_resp(resp, request_id="2")
            if not resp.headers:
                self._fail_and_log_resp(resp, request_id="2", message="Got no headers")
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("2: Got no redirect location 2")
                # logger.info(f"2: Error {resp.content.decode('ascii')}")
            logger.debug(f"2: Completed {resp.request.url}")
        with self.client.request(
            "GET",
            redirect_location,
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "Referer": "https://fidustest.skolverket.se/",
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
            catch_response=True,
            allow_redirects=False,
            name="3:https://ds.fidus.skolverket.se/ds/?entityID=https%3A%2F%2Fidpproxy.dev.eduid.se%2Fsp&return=https%3A%2F%2Fidpproxy.dev.eduid.se%2FSaml2SP%2Fdisco"[
                :60
            ],
        ) as resp:
            if not resp.ok:
                self._fail_and_log_resp(resp, request_id="3")
            logger.debug(f"Completed {resp.request.url}")
        with self.client.request(
            "GET",
            "https://idpproxy.dev.eduid.se/Saml2SP/disco?entityID=https://dummy-sto3-test-idp.sunet.se/simplesaml/saml2/idp/metadata.php",
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "referer": "https://ds.fidus.skolverket.se/",
                "user-agent": USER_AGENT,
            },
            catch_response=True,
            allow_redirects=False,
            name="4:https://idpproxy.dev.eduid.se/Saml2SP/disco?entityID=https://dummy-sto3-test-idp.sunet.se/simplesaml/saml2/idp/metadata.php"[
                :60
            ],
        ) as resp:
            if not resp.ok:
                self._fail_and_log_resp(resp, request_id="4")
            if not resp.headers:
                self._fail_and_log_resp(resp, request_id="4", message="Got no headers")
            redirect_location = resp.headers.get("location")
            if redirect_location is None:
                resp.failure("Got no redirect location 3")
                logger.info(f"4: Code = {resp.status_code}")
            logger.debug(f"4: Completed {resp.request.url}")
        with self.client.request(
            "GET",
            redirect_location,
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "referer": "https://ds.fidus.skolverket.se/",
                "user-agent": USER_AGENT,
                "x-requested-with": "XMLHttpRequest",
            },
            catch_response=True,
            allow_redirects=False,
            name="5:https://dummy-sto3-test-idp.sunet.se/simplesaml/module.php/saml/idp/singleSignOnService?SAMLRequest={saml_request}&RelayState={relay_state}"[
                :60
            ],
        ) as resp:
            if not resp.ok:
                self._fail_and_log_resp(resp, request_id="5")
            try:
                post_match = re.search(
                    r'name="SAMLResponse" value="[\w+=]+', resp.content.decode("ascii")
                )
            except UnicodeDecodeError:
                logger.info(f"5: Content = {resp.content}")
                resp.failure("5: Content unicode decode error")
            if post_match:
                saml_response = post_match.group()[27:]
            else:
                resp.failure("5: Didn't get any SAMLResponse in form")
            try:
                post_match = re.search(
                    r'name="RelayState" value="[\w+=]+', resp.content.decode("ascii")
                )
            except UnicodeDecodeError:
                logger.info(f"5: Content = {resp.content}")
                resp.failure("5: Content unicode decode error")
            if post_match:
                relay_state = post_match.group()[25:]
            else:
                resp.failure("5: Didn't get any RelayState in form")
            logger.debug(f"5: Completed {resp.request.url}")
        with self.client.request(
            "POST",
            "https://idpproxy.dev.eduid.se/Saml2SP/acs/post",
            headers={
                "accept": ACCEPT_HTML,
                "accept-encoding": ACCEPT_ENCODING,
                "referer": "https://dummy-sto3-test-idp.sunet.se/",
                "user-agent": USER_AGENT,
                "Content-Type": "application/x-www-form-urlencoded",
            },
            data=urlencode({"SAMLResponse": saml_response, "RelayState": relay_state}),
            catch_response=True,
            allow_redirects=False,
            name="6:https://idpproxy.dev.eduid.se/Saml2SP/acs/post"[:60],
        ) as resp:
            if not resp.ok:
                self._fail_and_log_resp(resp, request_id="6")
            if not resp.content:
                self._fail_and_log_resp(
                    resp, request_id="6", message="No content in response"
                )
            try:
                post_match = re.search(
                    r'name="SAMLResponse" value="[\w+=]+', resp.content.decode("ascii")
                )
            except UnicodeDecodeError:
                logger.info(f"6: Content = {resp.content}")
                resp.failure("6: Content unicode decode error")
            if not post_match:
                resp.failure("6: Did't get any SAMLResponse in form")
            logger.debug(f"6: Completed {resp.request.url}")


if __name__ == "__main__":
    run_single_user(fidustest_skolverket_se)
