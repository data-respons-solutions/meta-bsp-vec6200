#!/bin/sh

ANI1_GPIO="/sys/class/gpio/gpio15/value"
ANI2_GPIO="/sys/class/gpio/gpio14/value"
ANI3_GPIO="/sys/class/gpio/gpio13/value"
ANI4_GPIO="/sys/class/gpio/gpio12/value"

die() {
	>&2 echo "${1}"
	exit 1
}

print_usage() {
    echo "Usage: ${1} CHANNEL [CHANNEL OPTIONS]"
    echo 
    echo "Available input channels:"
    echo "channel: OPTIONS"
    echo "ani1:   [--voltage | --current]"
    echo "ani2:   [--voltage | --current]"
    echo "ani3:   [--voltage | --current]"
    echo "ani4:   [--voltage | --current]"
    echo 
    echo "Channel options:"
    echo "--voltage      Voltage measurement"
    echo "--current      Current measurement"
    exit 0
}

if [ "${#}" -lt "2" ]; then
	print_usage "$(basename ${0})"
	die "Invalid argument"
fi


read_voltage() {
	gpio="${1}"
	device="${2}"
	echo 0 >"${gpio}" || die "Failed setting mode select gpio"
	echo "$(iio_attr -q -c ${device} voltage0 raw) * $(iio_attr -q -c ${device} voltage0 scale)" | bc || die "Failed reading input"
}

read_current() {
	gpio="${1}"
	device="${2}"
	echo 1 >"${gpio}" || die "Failed setting mode select gpio"
	echo "$(iio_attr -q -c ${device} current0 raw) * $(iio_attr -q -c ${device} current0 scale)" | bc || die "Failed reading input"
}

channel="${1}"
mode=""
if [ "${2}" = "--voltage" ]; then
	mode="voltage"
elif [ "${2}" = "--current" ]; then
	mode="current"
else
	print_usage "$(basename ${0})"
	die "Invalid argument"
fi

case "${channel}" in
	ani1)
		if [ "${mode}" = "voltage" ]; then
			read_voltage "${ANI1_GPIO}" "ani1-voltage"
		else
			read_current "${ANI1_GPIO}" "ani1-current"
		fi
		;;
	ani2)
		if [ "${mode}" = "voltage" ]; then
			read_voltage "${ANI2_GPIO}" "ani2-voltage"
		else
			read_current "${ANI2_GPIO}" "ani2-current"
		fi
		;;
	ani3)
		if [ "${mode}" = "voltage" ]; then
			read_voltage "${ANI3_GPIO}" "ani3-voltage"
		else
			read_current "${ANI3_GPIO}" "ani3-current"
		fi
		;;
	ani4)
		if [ "${mode}" = "voltage" ]; then
			read_voltage "${ANI4_GPIO}" "ani4-voltage"
		else
			read_current "${ANI4_GPIO}" "ani4-current"
		fi
		;;
	*)
		print_usage "$(basename ${0})"
		die "Invalid argument"
		;;
esac

exit 0