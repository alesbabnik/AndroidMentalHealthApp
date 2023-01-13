package org.tensorflow.lite.examples.textclassification

class QuestionModel {
    var question: String? = null
    var answer: String? = null

    constructor(question: String?, answer: String?) {
        this.question = question
        this.answer = answer
    }
}