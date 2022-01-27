package com.example.beer2beer.callback

abstract class Callback {
    abstract fun onSuccess(`object`: Any?)
    abstract fun onError(`object`: Any?)
}