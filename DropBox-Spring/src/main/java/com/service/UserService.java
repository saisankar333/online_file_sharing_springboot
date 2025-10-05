package com.service;

import com.entity.CreateShareFolder;
import com.entity.Groups;
import com.entity.Sharewith;
import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public File[] getAllUsers1(String userfolder) {
        File folder = new File("./" + userfolder);
        return folder.listFiles();
    }

    public void addUser(User user) {
        userRepository.save(user);
        String folder = user.getEmail();
        new File("./" + folder).mkdir();
    }

    public List<User> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public boolean deletefile(String filename, String userFolder) {
        Path deleter = Paths.get("./" + userFolder + "/" + filename);
        try {
            return Files.deleteIfExists(deleter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sharefile(Sharewith sharewith) {
        Optional<User> optionalUser = userRepository.findById(1);
        Set<Groups> userGroups = optionalUser.map(User::getGroups).orElse(Collections.emptySet());

        Path src = Paths.get("./" + sharewith.getOwner() + "/" + sharewith.getFilename());
        String[] shareinfo = sharewith.getEmails().split(",");

        for (String email : shareinfo) {
            Path dest = Paths.get("./" + email + "/" + sharewith.getFilename());
            try {
                Files.createSymbolicLink(dest, src);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void uploader(MultipartFile file, String userfolder) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("./" + userfolder + "/" + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createShareFolder(CreateShareFolder createShareFolder, String sharefolder) {
        new File("./" + sharefolder + "/" + createShareFolder.getFoldername()).mkdir();
        Path src = Paths.get("./" + sharefolder + "/" + createShareFolder.getFoldername());
        String[] shareinfo = createShareFolder.getEmails().split(",");

        for (String email : shareinfo) {
            Path dest = Paths.get("./" + email + "/" + createShareFolder.getFoldername());
            try {
                Files.createSymbolicLink(dest, src);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createFolder(CreateShareFolder createShareFolder, String sharefolder) {
        new File("./" + sharefolder + "/" + createShareFolder.getFoldername()).mkdir();
    }
}