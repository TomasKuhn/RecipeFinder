package com.tkuhn.recipefinder.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
fun notNull(a: Any?, b: Any?): Boolean {
    contract {
        returns(true) implies (a is Any && b is Any)
    }
    return a != null && b != null
}

@ExperimentalContracts
fun notNull(a: Any?, b: Any?, c: Any?): Boolean {
    contract {
        returns(true) implies (a is Any && b is Any && c is Any)
    }
    return a != null && b != null && c != null
}

@ExperimentalContracts
fun notNull(a: Any?, b: Any?, c: Any?, d: Any?): Boolean {
    contract {
        returns(true) implies (a is Any && b is Any && c is Any && d is Any)
    }
    return a != null && b != null && c != null && d != null
}