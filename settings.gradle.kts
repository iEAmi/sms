rootProject.name = "sms"

// Domain modules
include(
    "sms-domain",
    "sms-domain:model",
)

// Application modules
include(
    "sms-app",
    "sms-app:rest-api",
    "sms-app:queue-api",
    "sms-app:defs",
    "sms-app:common",
)

// Infrastructure modules
include(
    "sms-infra",
    "sms-infra:persistence",
    "sms-infra:queue"
)

// Launcher
include("sms-launcher")