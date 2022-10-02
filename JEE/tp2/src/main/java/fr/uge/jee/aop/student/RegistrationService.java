package fr.uge.jee.aop.student;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class RegistrationService {


    private final HashMap<Long,Student> studentsById = new HashMap<>();
    private final Map<Long,Lecture> lecturesById = new HashMap<>();
    private long currentStudentId;
    private long currentLectureId;


    /**
     *
     * @return studentId
     */
    public long createStudent(String firstName,String lastName){
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Student student = new Student(currentStudentId,firstName,lastName);
        studentsById.put(currentStudentId,student);
        return currentStudentId++;
    }

    public long createLecture(String title){
        Objects.requireNonNull(title);
        Lecture lecture = new Lecture(currentLectureId,title);
        lecturesById.put(currentLectureId,lecture);
        return currentLectureId++;
    }


    public void register(long studentId, long lectureId){
        Lecture lecture = lecturesById.get(lectureId);
        if (lecture==null){
            throw new IllegalStateException("No such lecture "+lectureId);
        }
        Student student = studentsById.get(studentId);
        if (student==null){
            throw new IllegalStateException("No student created with id "+studentId);
        }
        student.registerToLecture(lecture);
    }

    public void loadFromDB() throws InterruptedException {
        Thread.sleep(100);
    }

    public void saveToDB() throws InterruptedException {
        Thread.sleep(200);
    }

    public void printReport() {
        for(Lecture lecture : lecturesById.values()){
            System.out.println(lecture.getTitle());
            for(Student student : lecture.getStudents()){
                System.out.println("\t"+student);
            }
        }
    }
}
