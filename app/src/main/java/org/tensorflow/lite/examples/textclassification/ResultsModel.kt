package org.tensorflow.lite.examples.textclassification

class ResultsModel {
    var question: String? = null
    var answer: String? = null
    var negative: Float? = null
    var positive: Float? = null

    constructor(question: String?, answer: String?, negative: Float?, positive: Float?) {
        this.question = question
        this.answer = answer
        this.negative = negative
        this.positive = positive
    }
}