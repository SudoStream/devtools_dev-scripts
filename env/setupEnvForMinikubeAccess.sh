#!/usr/bin/env bash

export USER_READER_SERVICE_PORT=`kubectl get services | grep "user-reader" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
echo "USER_READER_SERVICE_PORT : ${USER_READER_SERVICE_PORT}"

export SCHOOL_READER_SERVICE_PORT=`kubectl get services | grep "school-reader" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
echo "SCHOOL_READER_SERVICE_PORT : ${SCHOOL_READER_SERVICE_PORT}"

export USER_WRITER_SERVICE_PORT=`kubectl get services | grep "user-writer" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
echo "USER_WRITER_SERVICE_PORT : ${USER_WRITER_SERVICE_PORT}"

export CLASSTIMETABLE_WRITER_SERVICE_PORT=`kubectl get services | grep "classtimetable-writer" | awk '{ print $5 }' | awk -F"," '{ print$1 }' | awk -F":" '{ print $2 }' | awk -F"/" '{ print $1}'`
echo "CLASSTIMETABLE_WRITER_SERVICE_PORT : ${CLASSTIMETABLE_WRITER_SERVICE_PORT}"
