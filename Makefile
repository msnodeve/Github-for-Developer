set_env:
	echo "GITHUB_CLIENT_ID=\"client_id\"" >> app/gradle.properties
	echo "GITHUB_CLIENT_SECRET=\"client_secret\"" >> app/gradle.properties
	echo "REDIRECT_CALLBACK_URL=\"gfd://github.for.developer\"" >> app/gradle.properties
	echo "PREFERENCES_FILE=\"file_name\"" >> app/gradle.properties
	echo "PREFERENCES_TOKEN_KEY=\"token_key\"" >> app/gradle.properties
	echo "BASIC_AUTH_KEY=\"basic_auth_key\"" >> app/gradle.properties