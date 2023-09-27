// Import the necessary classes for ReadyAPI
import com.eviware.soapui.support.JsonUtil
import groovy.json.JsonSlurper

// Define the expected JSON data as a string
def requestBody = mockRequest.getRequestContent()
log.info "Request body: " + requestBody

// Parse the JSON data directly from the request body
def actualJsonObject

try {
    actualJsonObject = new groovy.json.JsonSlurper().parseText(requestBody)
} catch (Exception e) {
    log.error("Failed to parse JSON from request body: ${e.message}")
}

// Define your validation criteria
def mandatoryProperties = ["type", "studentName", "studentId", "studentEmail", "notify", "enrolmentDetails", "enrollment_state", "accountId"]

def studentIdRegex = /^E\d{5}$/
def emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/
def courseConnectionIdRegex = /^a\d{3}D\d{6}L\w{4}$/

// Perform your custom validation checks here

boolean isValid = true

List<String> validationErrors = [] 

// Check if all mandatory properties exist in the JSON object

for (String property : mandatoryProperties) {
    if (!actualJsonObject.containsKey(property)) {
        isValid = false
        return "401"
    }

	// Validate 'studentId' with a regular expression
    def studentId = actualJsonObject.studentId
    if (!(studentId =~ studentIdRegex)) {
        isValid = false
        return "406"
    }

    // Validate 'studentEmail' with a regular expression
    def studentEmail = actualJsonObject.studentEmail
    if (!(studentEmail =~ emailRegex)) {
        isValid = false
        return "406"
    }

    // Validate 'courseConnectionId' with a regular expression
    def courseConnectionId = actualJsonObject.enrolmentDetails.courseConnectionId
    if (!(courseConnectionId =~ courseConnectionIdRegex)) {
        isValid = false
        return "406"
    } 

    if (isValid) {
        // Return a response with a status code of 200 if validation passes
        return "200"
    }
}