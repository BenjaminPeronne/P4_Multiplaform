package com.example.p4_multiplaform

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}