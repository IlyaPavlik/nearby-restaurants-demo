package com.example.restaurants.domain.common.ext

import org.slf4j.LoggerFactory

fun Any.logger(tag: String? = null) = lazy { LoggerFactory.getLogger(tag ?: javaClass.simpleName) }