package com.example.volumeintimemanager.utils

fun Int.convertToTwoDigitsString(): String {
    return if (this < 10)
        "0${this}"
    else
        this.toString()
}