def jobName = "sample_job"
def dsplName = "Sample Job"
def repoNamespace = 'laenger'
def repoName = 'JobDslGradleProject'

job(jobName) {
    displayName(dsplName)
    scm {
        git {
            remote {
                name('origin')
                url("git@github.com:${repoNamespace}/${repoName}.git")
                credentials('github-ssh')
            }
        }
    }

    wrappers {
        timestamps()
    }

    steps {
        gradle {
            useWrapper()
            makeExecutable()
            switches('--stacktrace')

            tasks("clean")
            tasks("build")
        }
    }

    publishers {
        androidLint('*/build/outputs/lint-*.xml')
        checkstyle('*/build/reports/checkstyle/*.xml')
        findbugs('*/build/reports/findbugs/*.xml')
        pmd('*/build/reports/pmd/*.xml')
        archiveJunit('*/build/test-results/**/*.xml')

        archiveArtifacts {
            pattern('**/*.apk')
        }
    }
}
