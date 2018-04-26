<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="public/css/main.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Sign in to continue..</h1>

            <div class="account-wall">

                <!--<div ng-if="Login.errorIs" class="msg-login-page text-center">{{Login.message}}</div>-->

                <img class="profile-img"
                     src="public/images/logo_img.png"/>

                <form class="form-signin" action="/login" method="post">

                    <input type="text" name='username' class="form-control" placeholder="Login" required
                           autofocus/>

                    <input type="password" name='password' class="form-control" placeholder="Password" required/>

                    <button class="btn btn-lg btn-primary btn-block" type="submit">
                        Sign in
                    </button>

                </form>
            </div>

        </div>
    </div>
</div>
</body>
</html>