#!/bin/sh

ANI1_GPIO="/sys/class/gpio/gpio15/value"
ANI1_VOLTAGE_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage2_raw"
ANI1_CURRENT_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage0_raw"
ANI2_GPIO="/sys/class/gpio/gpio14/value"
ANI2_VOLTAGE_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage3_raw"
ANI2_CURRENT_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage1_raw"
ANI3_GPIO="/sys/class/gpio/gpio13/value"
ANI3_VOLTAGE_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage6_raw"
ANI3_CURRENT_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage4_raw"
ANI4_GPIO="/sys/class/gpio/gpio12/value"
ANI4_VOLTAGE_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage7_raw"
ANI4_CURRENT_IIODEV="/sys/bus/iio/devices/iio:device2/in_voltage5_raw"


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
	iiodev="${2}"
	echo 0 >"${gpio}" || die "Failed setting mode select gpio"
	echo "($(cat ${iiodev}) * 2500) / (4096 * 43/793)" | bc || die "Failed reading input"
}

read_current() {
	gpio="${1}"
	iiodev="${2}"
	echo 1 >"${gpio}" || die "Failed setting mode select gpio"
	echo "($(cat ${iiodev}) * 2500) / 409.6" | bc || die "Failed reading input"
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
			read_voltage "${ANI1_GPIO}" "${ANI1_VOLTAGE_IIODEV}"
		else
			read_current "${ANI1_GPIO}" "${ANI1_CURRENT_IIODEV}"
		fi
		;;
	ani2)
		if [ "${mode}" = "voltage" ]; then
			read_voltage "${ANI2_GPIO}" "${ANI2_VOLTAGE_IIODEV}"
		else
			read_current "${ANI2_GPIO}" "${ANI2_CURRENT_IIODEV}"
		fi
		;;
	ani3)
		if [ "${mode}" = "voltage" ]; then
			read_voltage "${ANI3_GPIO}" "${ANI3_VOLTAGE_IIODEV}"
		else
			read_current "${ANI3_GPIO}" "${ANI3_CURRENT_IIODEV}"
		fi
		;;
	ani4)
		if [ "${mode}" = "voltage" ]; then
			read_voltage "${ANI4_GPIO}" "${ANI4_VOLTAGE_IIODEV}"
		else
			read_current "${ANI4_GPIO}" "${ANI4_CURRENT_IIODEV}"
		fi
		;;
	*)
		print_usage "$(basename ${0})"
		die "Invalid argument"
		;;
esac

exit 0