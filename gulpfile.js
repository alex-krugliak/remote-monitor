var gulp = require('gulp');
var gp_uglify = require('gulp-uglify');
var gp_concat = require('gulp-concat');
var clean = require('gulp-clean');
var cssmin = require('gulp-cssmin');


gulp.task('clean', function() {
    gulp.src('./src/main/resources/static/public')
        .pipe(clean());
    
});

gulp.task('build', function() {

    gulp.src(['./bower_components/jquery/dist/jquery.min.js',
        './bower_components/bootstrap/dist/js/bootstrap.min.js',
        './bower_components/underscore/underscore-min.js',
        './bower_components/angular/angular.min.js',
        './bower_components/angular-ui-router/release/angular-ui-router.min.js',
        './bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
        './bower_components/angular-block-ui/dist/angular-block-ui.min.js',
        './bower_components/angular-breadcrumb/release/angular-breadcrumb.min.js',
        './bower_components/angularjs-toaster/toaster.min.js',
        './bower_components/angular-sanitize/angular-sanitize.min.js'])
        .pipe(gp_concat('vendor.js'))
        //.pipe(gp_uglify())
        .pipe(gulp.dest('./src/main/resources/static/public/js'));

    gulp.src(['./web-ui/js/**/*.js'])
        .pipe(gp_concat('main.js'))
        //.pipe(gp_uglify())
        .pipe(gulp.dest('./src/main/resources/static/public/js'));



    gulp.src(['./bower_components/bootstrap/dist/css/bootstrap.min.css',
        './bower_components/angular-block-ui/dist/angular-block-ui.min.css',
        './bower_components/angularjs-toaster/toaster.min.css', './web-ui/css/main.css'])
        .pipe(gp_concat('main.css'))
        //.pipe(cssmin())
        .pipe(gulp.dest('./src/main/resources/static/public/css'));

    gulp.src(['./bower_components/bootstrap/dist/fonts/*'])
        .pipe(gulp.dest('./src/main/resources/static/public/fonts'));

    gulp.src(['./web-ui/js/templates/*'])
        .pipe(gulp.dest('./src/main/resources/static/public/js/templates'));

    gulp.src(['./web-ui/images/*'])
        .pipe(gulp.dest('./src/main/resources/static/public/images'));

});








