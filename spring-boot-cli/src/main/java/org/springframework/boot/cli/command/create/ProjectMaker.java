package org.springframework.boot.cli.command.create;

import org.codehaus.plexus.util.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class ProjectMaker {
    private String projectPath;
    private String projectName;

    public ProjectMaker(String projectName) {
        this.projectName = projectName;
        this.projectPath = "./" + projectName;
    }

    public void create() {
        try {
            createProjectDirectory();
            createSrcDirectory();
            createConfigurationFiles();
            createApplication();
            createBuildFile();
            createSourceControlFiles();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSourceControlFiles() throws IOException, InterruptedException {
        runCommand("touch " + projectPath + "/.gitignore");

        ClassPathResource resource = new ClassPathResource("templates/commands/create/gitignore");
        FileUtils.copyFile(resource.getFile(), new File(projectPath + "/.gitignore"));
    }

    private void createBuildFile() throws IOException, InterruptedException {
        runCommand("touch " + projectPath + "/build.gradle");

        ClassPathResource resource = new ClassPathResource("templates/commands/create/build.gradle");
        FileUtils.copyFile(resource.getFile(), new File(projectPath + "/build.gradle"));
    }

    private void createApplication() throws IOException, InterruptedException {
        runCommand("touch " + projectPath + "/src/main/java/" + projectName + "/Application.java");

        ClassPathResource resource = new ClassPathResource("templates/commands/create/Application.java");
        FileUtils.copyFile(resource.getFile(), new File(projectPath + "/src/main/java/" + projectName + "/Application.java"));
    }

    private void createConfigurationFiles() throws IOException, InterruptedException {
        String configurationPath = projectPath + "/src/main/resources/config";

        runCommand("touch " + configurationPath + "/application-development.yml");
        runCommand("touch " + configurationPath + "/application-test.yml");
        runCommand("touch " + configurationPath + "/application-production.yml");
        runCommand("touch " + configurationPath + "/application.yml");
    }

    private void createSrcDirectory() throws IOException, InterruptedException {
        runCommand("mkdir -p " + projectPath + "/src/main/java/" + projectName);
        runCommand("mkdir -p " + projectPath + "/src/main/resources/config");
        runCommand("mkdir -p " + projectPath + "/src/test");
    }

    private void createProjectDirectory() throws IOException, InterruptedException {
        runCommand("mkdir " + projectPath);
    }

    private void runCommand(String command) throws InterruptedException, IOException {
        Runtime.getRuntime().exec(command).waitFor();
    }
}
