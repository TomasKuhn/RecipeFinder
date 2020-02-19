package com.tkuhn.recipefinder.repository.mapper

interface ModelMapper<I, O> {
    fun map(input: I): O
}
