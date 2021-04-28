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
    "sms-app:defs",
    "sms-app:common",
)

// Infrastructure modules
include(
    "sms-infra",
    "sms-infra:persistence",
)

// Launcher
include("sms-launcher")
