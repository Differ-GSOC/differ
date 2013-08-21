#!/bin/bash
eval "exec jhove -h xml $1 | grep -v "READBOX""
