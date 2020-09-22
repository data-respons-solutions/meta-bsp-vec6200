#!/bin/sh

die() {
	>&2 echo "${1}"
	exit 1
}

if [ $# -lt 1 ]; then
	echo "Usage: status-led [red|green|yellow|off] [blink]"
	exit 1
fi

echo "none" > /sys/class/leds/status-green/trigger || die "Failed disabling trigger"
echo "none" > /sys/class/leds/status-red/trigger || die "Failed disabling trigger"
	
case $1 in
	red)
		echo 1 > /sys/class/leds/status-red/brightness || die "Failed setting led"
		echo 0 > /sys/class/leds/status-green/brightness || die "Failed setting led"
		;;
		
	green)
		echo 1 > /sys/class/leds/status-green/brightness || die "Failed setting led"
		echo 0 > /sys/class/leds/status-red/brightness || die "Failed setting led"
		;;
		
	yellow)
		echo 1 > /sys/class/leds/status-green/brightness || die "Failed setting led"
		echo 1 > /sys/class/leds/status-red/brightness || die "Failed setting led"
		;;
	
	off)
		echo 0 > /sys/class/leds/status-green/brightness || die "Failed setting led"
		echo 0 > /sys/class/leds/status-red/brightness || die "Failed setting led"
		;;	
esac

if [ $# -eq 2 ] && [ $2 = "blink" ]; then
	case $1 in 
		red)
			echo "heartbeat" > /sys/class/leds/status-red/trigger || die "Failed setting trigger"
			echo "none" > /sys/class/leds/status-green/trigger || die "Failed setting trigger"
			;;
			
		green)
			echo "none" > /sys/class/leds/status-red/trigger || die "Failed setting trigger"
			echo "heartbeat" > /sys/class/leds/status-green/trigger || die "Failed setting trigger"
			;;
			
		yellow)
			echo "heartbeat" > /sys/class/leds/status-red/trigger || die "Failed setting trigger"
			echo "heartbeat" > /sys/class/leds/status-green/trigger || die "Failed setting trigger"
			;;
	esac
fi

exit 0