package fr.uge.jee.hibernate.cinema;

import fr.uge.jee.hibernate.cinema.video.Video;
import fr.uge.jee.hibernate.cinema.video.VideoRepository;
import fr.uge.jee.hibernate.cinema.viewer.Viewer;
import fr.uge.jee.hibernate.cinema.viewer.ViewerRepository;
import java.util.HashSet;

public class Application {

    public static void main(String[] args) {
        var videoRepo = VideoRepository.instance();
        var viewerRepo = ViewerRepository.instance();

        var matrix = new Video("The Matrix", new HashSet<>());
        var dpTuto = new Video("Dp Tutorial", new HashSet<>());
        videoRepo.create(matrix);
        videoRepo.create(dpTuto);

        var jim = new Viewer("Jim");
        var bob = new Viewer("Bob");
        viewerRepo.create(jim);
        viewerRepo.create(bob);

        videoRepo.addUpvote(matrix, jim);
        videoRepo.addUpvote(matrix, bob);
        videoRepo.addUpvote(dpTuto, jim);
        videoRepo.addDownvote(dpTuto, bob);

        System.out.println(videoRepo.getScore(matrix));
        System.out.println(videoRepo.getScore(dpTuto));
    }
}
