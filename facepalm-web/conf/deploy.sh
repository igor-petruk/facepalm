export DEPLOY_PROJECT_PATH=/home/hagen/git/socialapps/social_app_template
export PEM_PATH=/home/hagen/Downloads/nginx-keypair.pem
export SERVER_CREDENTIALS=ubuntu@ec2-50-16-197-208.compute-1.amazonaws.com
export SERVER_PATH=/home/ubuntu/


rsync -arvuz $DEPLOY_PROJECT_PATH -e "ssh -i $PEM_PATH" $SERVER_CREDENTIALS:$SERVER_PATH --exclude '.*' --exclude 'tmp' --exclude 'eclipse'