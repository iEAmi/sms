package me.ahmad.sms.defs

import com.fasterxml.jackson.annotation.JsonProperty

data class ProblemDetails(
    @JsonProperty("status")
    val status: Int,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("details")
    val details: String?
)
