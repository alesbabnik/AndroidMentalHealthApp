package org.tensorflow.lite.examples.textclassification

class ResultsModel {
    var question: String? = null
    var answer: String? = null
    var result: Float? = null

    constructor(question: String?, answer: String?, result: Float?) {
        this.question = question
        this.answer = answer
        this.result = result
    }
}