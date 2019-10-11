// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  
  apiPath: "http://localhost:8080",

  DEFAULT_AUTH_URL: 'http://localhost:4200/login',
  GOOGLE_AUTH_URL: 'http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhost:4200/home',
  FACEBOOK_AUTH_URL: 'http://localhost:8080/oauth2/authorize/facebook?redirect_uri=http://localhost:4200/home',
  GITHUB_AUTH_URL: 'http://localhost:8080/oauth2/authorize/github?redirect_uri=http://localhost:4200/home',
};
