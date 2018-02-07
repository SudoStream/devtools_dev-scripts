export USER_READER_SERVICE_PORT=`kubectl get services | grep "user-reader" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
export USER_READER_SERVICE_HOST=`minikube service "user-reader" --url | grep ${USER_READER_SERVICE_PORT} | awk -F"/" '{ print $3 }' | awk -F":" '{ print $1 }'`
echo "USER_READER_SERVICE : ${USER_READER_SERVICE_HOST}:${USER_READER_SERVICE_PORT}"

export SCHOOL_READER_SERVICE_PORT=`kubectl get services | grep "school-reader" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
export SCHOOL_READER_SERVICE_HOST=`minikube service "school-reader" --url | grep ${SCHOOL_READER_SERVICE_PORT} | awk -F"/" '{ print $3 }' | awk -F":" '{ print $1 }'`
echo "SCHOOL_READER_SERVICE : ${SCHOOL_READER_SERVICE_HOST}:${SCHOOL_READER_SERVICE_PORT}"

export USER_WRITER_SERVICE_PORT=`kubectl get services | grep "user-writer" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
export USER_WRITER_SERVICE_HOST=`minikube service "user-writer" --url | grep ${USER_WRITER_SERVICE_PORT} | awk -F"/" '{ print $3 }' | awk -F":" '{ print $1 }'`
echo "USER_WRITER_SERVICE : ${USER_WRITER_SERVICE_HOST}:${USER_WRITER_SERVICE_PORT}"

export CLASSTIMETABLE_WRITER_SERVICE_PORT=`kubectl get services | grep "classtimetable-writer" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
export CLASSTIMETABLE_WRITER_SERVICE_HOST=`minikube service "classtimetable-writer" --url | grep ${CLASSTIMETABLE_WRITER_SERVICE_PORT} | awk -F"/" '{ print $3 }' | awk -F":" '{ print $1 }'`
echo "CLASSTIMETABLE_WRITER_SERVICE : ${CLASSTIMETABLE_WRITER_SERVICE_HOST}:${CLASSTIMETABLE_WRITER_SERVICE_PORT}"

export CLASSTIMETABLE_READER_SERVICE_PORT=`kubectl get services | grep "classtimetable-reader" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
export CLASSTIMETABLE_READER_SERVICE_HOST=`minikube service "classtimetable-reader" --url | grep ${CLASSTIMETABLE_READER_SERVICE_PORT} | awk -F"/" '{ print $3 }' | awk -F":" '{ print $1 }'`
echo "CLASSTIMETABLE_READER_SERVICE : ${CLASSTIMETABLE_READER_SERVICE_HOST}:${CLASSTIMETABLE_READER_SERVICE_PORT}"

export ES_AND_OS_READER_SERVICE_PORT=`kubectl get services | grep "es-and-os-reader" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
export ES_AND_OS_READER_SERVICE_HOST=`minikube service "es-and-os-reader" --url | grep ${ES_AND_OS_READER_SERVICE_PORT} | awk -F"/" '{ print $3 }' | awk -F":" '{ print $1 }'`
echo "ES_AND_OS_READER_SERVICE : ${ES_AND_OS_READER_SERVICE_HOST}:${ES_AND_OS_READER_SERVICE_PORT}"
