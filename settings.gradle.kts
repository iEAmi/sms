rootProject.name = "sms"
include(
    "sms-app",
    "sms-app:rest-api",
    "sms-domain",
    "sms-domain:common",
    "sms-domain:model",
    "sms-infra",
    "sms-launcher"
)
