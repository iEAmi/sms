rootProject.name = "sms"
include(
    "sms-app",
    "sms-app:rest-api",
    "sms-app:defs",
    "sms-app:common",

    "sms-domain",
    "sms-domain:model",

    "sms-infra",
    "sms-launcher"
)
