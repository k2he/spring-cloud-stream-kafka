export const environment = {
  production: true,

  apiPath: "http://localhost:8080",

  DEFAULT_AUTH_URL: 'http://localhost:4200/login',
  GOOGLE_AUTH_URL: 'http://localhost:8080/auth/oauth2/authorize/google?redirect_uri=http://localhost:4200/home',
  FACEBOOK_AUTH_URL: 'http://localhost:8080/auth/oauth2/authorize/facebook?redirect_uri=http://localhost:4200/home',
  GITHUB_AUTH_URL: 'http://localhost:8080/auth/oauth2/authorize/github?redirect_uri=http://localhost:4200/home',

};
