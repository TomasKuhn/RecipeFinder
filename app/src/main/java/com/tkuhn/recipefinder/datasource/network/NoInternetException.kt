package com.tkuhn.recipefinder.datasource.network

import java.io.IOException

class NoInternetException(url: String) : IOException(url)