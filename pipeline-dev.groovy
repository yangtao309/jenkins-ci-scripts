#!groovy

// dev environment pipeline settings
def do() {
stage('Code Quality')
node { echo 'code quality execute' }
parallel(CheckStyle: {
    node {
        sh 'gradle checkstyleMain'
        step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/checkstyle/main.xml', unHealthy: ''])
    }
}, FindBugs: {
    node {
        checkout scm
        unstash 'scripts'
        sh 'sh reset-build-gradle.sh'
        sh 'gradle findbugsMain'
        step([$class: 'FindBugsPublisher', canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/findbugs/main.xml', unHealthy: ''])
    }
}, Pmd: {
    node {
        sh 'gradle pmdMain'
        step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd/main.xml', unHealthy: ''])
    }
})


stage('Unit Test')
node { sh 'gradle clean test' }
parallel(TestNG: {
    node {
        echo 'testng test'
        step([$class: 'Publisher', reportFilenamePattern: '**/reports/testng/testng-results.xml'])
    }
}, Junit: {
     node {
        checkout scm
        unstash 'scripts'
        sh 'sh reset-build-gradle.sh'
        sh 'gradle clean test'
        echo 'junit test'
        step([$class: 'JUnitResultArchiver', testResults: '**/test-results/TEST-*.xml'])
    }
}, Jacoco: {
    node {
        echo 'jacoco reports generator'
    }
})

}

