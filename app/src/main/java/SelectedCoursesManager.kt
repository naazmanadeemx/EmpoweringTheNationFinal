object SelectedCoursesManager {
    private val selectedCourses = mutableListOf<String>()

    fun addCourse(course: String) {
        if (!selectedCourses.contains(course)) {
            selectedCourses.add(course)
        }
    }

    fun removeCourse(course: String) {
        selectedCourses.remove(course)
    }

    fun getSelectedCourses(): List<String> {
        return selectedCourses
    }

    // Add this method to clear all selected courses
    fun clearCourses() {
        selectedCourses.clear()
    }
}
