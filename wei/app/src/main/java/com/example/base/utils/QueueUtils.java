package com.example.base.utils;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import model.Student;

/**
 * Created by Administrator on 2016/2/16.
 */
public class QueueUtils {

    public static void soft(){
        Queue<Student> queue = new PriorityQueue(100,comparator);
        addDataToQueue(queue);
        getDataToQueue(queue);
    }

    //比较器
    private static Comparator<Student> comparator = new Comparator<Student>() {
        @Override
        public int compare(Student lhs, Student rhs) {
            return lhs.getId() - rhs.getId();
        }
    };

    private static void addDataToQueue( Queue<Student> queue){

        queue.add(new Student(10,"bao"));
        queue.add(new Student(41,"bao"));
        queue.add(new Student(21,"bao"));
        queue.add(new Student(18,"bao"));
    }

    private static int getDataToQueue( Queue<Student> queue){

        while (true) {
            Student student = queue.poll();
            if (student == null) {
                break;
            }
            return student.getId();
        }
        return 0;
    }
}
