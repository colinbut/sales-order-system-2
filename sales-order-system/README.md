# Sales Order System Client

This frontend project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Local Mode

In the project directory, you can run:

### Start Webpack Dev Server:

```bash
npm start
```

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br />
You will also see any lint errors in the console.

## Development Mode

Can simply do:

```bash
npm run build && npx serve -s build
```

or Alternatively, can run frontend stack using Docker:

```bash
docker-compose up -d --build sales-order-system-client
```

## Project Lifecyle

Currently, for this frontend client project there are only 2 known development lifecycle phases (as mainly provided by React's Create-React-App):

1. local
2. prod
   
Apart from local build for local development, everything else at the moment is considered 'production'.

### Local Build

```bash
npm install
```

### Run Tests

```bash
npm test
```

Note* currently no frontend tests as client mainly consists of UI (views) with minimal logic so will add some tests in later.

Launches the test runner in the interactive watch mode.<br />
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.


### Building for Prod

```bash
npm run build
```

Builds the app for production to the `build` folder.<br />
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

Once the static artifacts are built, then need to build a Docker image from it. The static files are served using a NGINX reverse proxy http server.

```bash
docker build -t sales-order-system-client .
```

Alternatively, reuse Docker-Compose capability of integrating the front end client into this whole project:

```bash
docker-compose up -d --build sales-order-system-client
```

## Technologies 

- React
- React-Router
- Material-UI
- React Bootstrap
- Material-Table
- Create-React-App
- Formik
- Yup
- Npm
- clsx

## Docs from Create-React-App's generated README

### Code Splitting

This section has moved here: https://facebook.github.io/create-react-app/docs/code-splitting

### Analyzing the Bundle Size

This section has moved here: https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size

### Making a Progressive Web App

This section has moved here: https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app

### Advanced Configuration

This section has moved here: https://facebook.github.io/create-react-app/docs/advanced-configuration

### Deployment

This section has moved here: https://facebook.github.io/create-react-app/docs/deployment

### `npm run build` fails to minify

This section has moved here: https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify
