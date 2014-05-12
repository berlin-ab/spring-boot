package org.springframework.boot.cli;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class CreateTaskIntegrationTests {

    private static String PROJECT_NAME = "pivotal-project";
    private static String PROJECT_PATH = "./" + PROJECT_NAME;

    String[] command = {"create", PROJECT_NAME};

    @Before
    @After
    public void deleteTestDirectory() {
        //runCommand("rm -rf ./" + PROJECT_NAME);
    }

    @Test
    public void itCreatesNewDirectory() throws Exception {
        SpringCli.main(command);

        File file = new File("./" + PROJECT_NAME);
        assertThat(file.isDirectory(), is(true));
    }

    public File getFile(String path) {
        return new File(PROJECT_PATH + path);
    }

    @Test
    public void itCreatesADirectoryForSourceCode() {
        SpringCli.main(command);

        File file = getFile("/src");
        assertThat(file.isDirectory(), is(true));

        file = getFile("/src/main");
        assertThat(file.isDirectory(), is(true));
    }

    @Test
    public void itCreatesADirectoryForTests() {
        SpringCli.main(command);

        File file = getFile("/src/test");
        assertThat(file.isDirectory(), is(true));
    }

    @Test
    public void itCreatesADirectoryForProductionJavaCode() {
        SpringCli.main(command);

        File file = getFile("/src/main/java");
        assertThat(file.isDirectory(), is(true));
    }

    @Test
    public void itCreatesAResourcesDirectory() {
        SpringCli.main(command);

        File file = getFile("/src/main/resources");
        assertThat(file.isDirectory(), is(true));
    }

    @Test
    public void itCreatesAConfigurationDirectory() {
        SpringCli.main(command);

        File file = getFile("/src/main/resources/config");
        assertThat(file.isDirectory(), is(true));
    }

    @Test
    public void itCreatesADevelopmentConfigurationFile() {
        SpringCli.main(command);

        File file = getFile("/src/main/resources/config/application-development.yml");
        assertThat(file.isFile(), is(true));
    }

    @Test
    public void itCreatesATestConfigurationFile() {
        SpringCli.main(command);

        File file = getFile("/src/main/resources/config/application-test.yml");
        assertThat(file.isFile(), is(true));
    }

    @Test
    public void itCreatesAProductionConfigurationFile() {
        SpringCli.main(command);

        File file = getFile("/src/main/resources/config/application-production.yml");
        assertThat(file.isFile(), is(true));
    }

    @Test
    public void itCreatesANonProfileSpecificConfigurationFile() {
        SpringCli.main(command);

        File file = getFile("/src/main/resources/config/application.yml");
        assertThat(file.isFile(), is(true));
    }

    @Test
    public void itCreatesAnPackageNamedAfterTheProject() {
        SpringCli.main(command);

        File file = getFile("/src/main/java/pivotal-project");
        assertThat(file.isDirectory(), is(true));
    }

    @Test
    public void itCreatesAnApplicationMainClass() {
        SpringCli.main(command);

        File file = getFile("/src/main/java/pivotal-project/Application.java");
        assertThat(file.isFile(), is(true));
    }

    @Test
    public void itCreatesAGradleBuildFile() {
        SpringCli.main(command);

        File file = getFile("/build.gradle");
        assertThat(file.isFile(), is(true));
    }

    @Test
    public void itCreatesAGitIgnoreFile() {
        SpringCli.main(command);

        File file = getFile("/.gitignore");
        assertThat(file.isFile(), is(true));
    }

    @Test
    public void itCompilesAndBuildsUsingSpringBootGradlePlugin() throws IOException {
        SpringCli.main(command);
        runCommand("cd ./pivotal-project; gradle build; gradle bootRun > outfile");

        String outfile = FileUtils.readFileToString(new File("./pivotal-project/outfile"));
        assertThat(outfile, containsString("BUILD SUCCESSFUL"));
    }

    private void runCommand(String arg) {
        try {
            Process process = Runtime.getRuntime().exec(arg);
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e){
            // do nothing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
