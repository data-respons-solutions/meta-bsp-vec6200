#!/bin/sh

die() {
	>&2 echo "${1}"
	exit 1
}

print_usage() {
    echo "Usage: ${1} I/O [I/O OPTIONS]"
    echo 
    echo "Available i/o:"
    echo "input"
    echo "di1"
    echo "di2"
    echo "di3"
    echo "di4"
    echo "di5"
    echo "di6"
    echo
    echo "output: OPTIONS"
    echo "aud1:   --sink    [0 | 1]"
    echo "aud2:   --sink    [0 | 1]"
    echo "aud3:   --sink    [0 | 1]"
    echo "do1:    --source  [0 | 1]"
    echo "do2:    --source  [0 | 1]"
    echo "do3:    --source  [0 | 1]"
    echo "do4:    --source  [0 | 1]"
    echo "do5:    --sink    [0 | 1]"
    echo "do6:    --sink    [0 | 1]"
    echo "do7:    --sink    [0 | 1]"
    echo "do8:    --sink    [0 | 1]"
    echo "do9:    --sink    [0 | 1]"
    echo 
    echo "options:"
    echo "--source      Source current, high side"
    echo "--sink        Sink current, low side"
    exit 0
}

if [ "${#}" -lt 1 ]; then
	print_usage "$(basename ${0})"
	die "Invalid argument"
fi

found="false"
dio="${1}"
for i in di1 di2 di3 di4 di5 di6; do
	if [ "${dio}" = "${i}" ]; then
		if [ "${#}" -ne 1 ]; then
			print_usage "$(basename ${0})"
			die "Invalid argument"
		fi
		found="true"
	fi
done

for i in aud1 aud2 aud3 do5 do6 do7 do8 do9; do
	if [ "${dio}" = "${i}" ]; then
		if [ "${#}" -ne 3 ] || [ "${2}" != "--sink" ]; then
			if [ "${3}" != "0" ] && [ "${3}" != "1" ]; then
				print_usage "$(basename ${0})"
				die "Invalid argument"
			fi
		fi
		found="true"
	fi
done

for i in do1 do2 do3 do4; do
	if [ "${dio}" = "${i}" ]; then
		if [ "${#}" -ne 3 ] || [ "${2}" != "--source" ]; then
			if [ "${3}" != "0" ] && [ "${3}" != "1" ]; then
				print_usage "$(basename ${0})"
				die "Invalid argument"
			fi
		fi
		found="true"
	fi
done

if [ "${found}" = "false" ]; then
	print_usage "$(basename ${0})"
	die "Invalid argument"
fi


case "${dio}" in
	di1)
		cat /sys/class/gpio/gpio1/value
		;;
	di2)
		cat /sys/class/gpio/gpio2/value
		;;
	di3)
		cat /sys/class/gpio/gpio5/value
		;;
	di4)
		cat /sys/class/gpio/gpio6/value
		;;
	di5)
		cat /sys/class/gpio/gpio147/value
		;;
	di6)
		cat /sys/class/gpio/gpio146/value
		;;
	aud1)
		echo "${3}" >/sys/class/gpio/gpio139/value
		;;
	aud2)
		echo "${3}" >/sys/class/gpio/gpio140/value
		;;
	aud3)
		echo "${3}" >/sys/class/gpio/gpio141/value
		;;
	do5)
		echo "${3}" >/sys/class/gpio/gpio122/value
		;;
	do6)
		echo "${3}" >/sys/class/gpio/gpio123/value
		;;
	do7)
		echo "${3}" >/sys/class/gpio/gpio124/value
		;;
	do8)
		echo "${3}" >/sys/class/gpio/gpio125/value
		;;
	do9)
		echo "${3}" >/sys/class/gpio/gpio138/value
		;;
	do1)
		echo "${3}" >/sys/class/gpio/gpio300/value
		;;
	do2)
		echo "${3}" >/sys/class/gpio/gpio301/value
		;;
	do3)
		die "do3 not unsupported"
		;;
	do4)
		die "do4 not unsupported"
		;;
	*)
		print_usage "$(basename ${0})"
		die "Invalid argument"
		;;
esac

exit 0