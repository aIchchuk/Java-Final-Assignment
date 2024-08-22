/*
Question1 a)

    Imagine you're a scheduling officer at a university with n classrooms numbered 0 to n-1. Several different courses
    require classrooms throughout the day, represented by an array of classes classes[i] = [starti, endi], where starti is
    the start time of the class and endi is the end time (both in whole hours). Your goal is to assign each course to a
    classroom while minimizing disruption and maximizing classroom utilization.
*/

import java.util.*;

public class ClassroomScheduler {

    public static int[] scheduleClasses(int n, int[][] classes) {
        // Sort classes by their start time
        Arrays.sort(classes, Comparator.comparingInt(a -> a[0]));
        
        // Priority queue to keep track of end times of classrooms
        // Each entry in the priority queue is a pair of (end time, classroom id)
        PriorityQueue<int[]> availableClassrooms = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        
        // Result array to store the classroom assignment for each class
        int[] assignments = new int[classes.length];
        
        for (int i = 0; i < classes.length; i++) {
            int start = classes[i][0];
            int end = classes[i][1];
            
            if (!availableClassrooms.isEmpty() && availableClassrooms.peek()[0] <= start) {
                // Reuse a classroom
                int[] room = availableClassrooms.poll();
                assignments[i] = room[1];
                availableClassrooms.offer(new int[]{end, room[1]});
            } else {
                // Need a new classroom
                int roomId = availableClassrooms.size();
                assignments[i] = roomId;
                availableClassrooms.offer(new int[]{end, roomId});
            }
        }
        
        return assignments;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the total number of classrooms: ");
        int n = scanner.nextInt();
        
        System.out.print("Enter the number of classes: ");
        int m = scanner.nextInt();
        
        int[][] classes = new int[m][2];
        
        System.out.println("Enter the start and end times of each class:");
        for (int i = 0; i < m; i++) {
            System.out.print("Class " + (i + 1) + " start time: ");
            classes[i][0] = scanner.nextInt();
            System.out.print("Class " + (i + 1) + " end time: ");
            classes[i][1] = scanner.nextInt();
        }
        
        int[] result = scheduleClasses(n, classes);
        
        System.out.println("Classroom assignments:");
        for (int i = 0; i < result.length; i++) {
            System.out.println("Class " + i + " -> Classroom " + result[i]);
        }
        
        scanner.close();
    }
}
