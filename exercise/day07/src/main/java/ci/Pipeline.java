package ci;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Logger;
import ci.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    private boolean projectDeployed;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
        projectDeployed = false;
    }

    public void run(Project project) {
        boolean testsPassed;

        testsPassed = runTests(project);

        deploy(project, testsPassed);

        summaryze(testsPassed);
    }

    private void summaryze(boolean testsPassed) {
        if (config.sendEmailSummary()) {
            log.info("Sending email");
            if (testsPassed) {
                if (projectDeployed) {
                    emailer.send("Deployment completed successfully");
                } else {
                    emailer.send("Deployment failed");
                }
            } else {
                emailer.send("Tests failed");
            }
        } else {
            log.info("Email disabled");
        }
    }

    private void deploy(Project project, boolean testsPassed) {
        if (testsPassed) {
            if ("success".equals(project.deploy())) {
                log.info("Deployment successful");
                projectDeployed = true;
            } else {
                log.error("Deployment failed");
            }
        }
    }

    private boolean runTests(Project project) {
        boolean testsPassed;
        if (project.hasTests()) {
            if ("success".equals(project.runTests())) {
                log.info("Tests passed");
                testsPassed = true;
            } else {
                log.error("Tests failed");
                testsPassed = false;
            }
        } else {
            log.info("No tests");
            testsPassed = true;
        }
        return testsPassed;
    }
}
