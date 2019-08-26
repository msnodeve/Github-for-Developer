set_env:
	echo "CLIENT_ID=\"client_id\"" >> app/gradle.properties
	echo "CLIENT_SECRET=\"client_secret\"" >> app/gradle.properties
	echo "CALLBACK_URL=\"gfd://github.for.developer\"" >> app/gradle.properties
	echo "PREFERENCES_FILE=\"file_name\"" >> app/gradle.properties
	echo "PREFERENCES_TOKEN_KEY=\"token_key\"" >> app/gradle.properties