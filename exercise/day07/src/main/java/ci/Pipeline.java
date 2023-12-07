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
    private boolean testFailed;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        runTests(project);
        deploy(project);
        summarize();
    }

    private void summarize() {
        if (!config.sendEmailSummary()) {
            log.info("Email disabled");
            return;
        }
        log.info("Sending email");
        if (testFailed) {
            emailer.send("Tests failed");
            return;
        }
        if (!projectDeployed) {
            emailer.send("Deployment failed");
            return;
        }
        emailer.send("Deployment completed successfully");
    }

    private void deploy(Project project) {
        if (testFailed)
            return;

        if ("success".equals(project.deploy())) {
            log.info("Deployment successful");
            projectDeployed = true;
        } else {
            log.error("Deployment failed");
        }
    }

    private void runTests(Project project) {
        if (!project.hasTests()) {
            log.info("No tests");
            return;
        }
        if (!project.runTests().equals("success")) {
            log.error("Tests failed");
            testFailed = true;
            return;
        }
        log.info("Tests passed");
    }
}
