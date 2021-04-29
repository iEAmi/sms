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
    "sms-app:event-handler"
)

// Infrastructure modules
include(
    "sms-infra",
    "sms-infra:persistence",
    "sms-infra:queue",
    "sms-infra:dispatcher",
)

// Launcher
include("sms-launcher")