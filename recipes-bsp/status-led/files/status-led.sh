#!/bin/sh

if [ $# -lt 1 ]; then
	echo "Usage: status-led [red|green|off]"
	exit 1
fi

echo "none" > /sys/class/leds/status-green/trigger
echo "none" > /sys/class/leds/status-red/trigger
	
case $1 in
	red)
		echo 1 > /sys/class/leds/status-red/brightness
		echo 0 > /sys/class/leds/status-green/brightness
		;;
		
	green)
		echo 1 > /sys/class/leds/status-green/brightness
		echo 0 > /sys/class/leds/status-red/brightness
		;;
		
	yellow)
		echo 1 > /sys/class/leds/status-green/brightness
		echo 1 > /sys/class/leds/status-red/brightness
		;;
	
	off)
		echo 0 > /sys/class/leds/status-green/brightness
		echo 0 > /sys/class/leds/status-red/brightness
		;;	
esac

if [ $# -eq 2 ] && [ $2 = "blink" ]; then
	case $1 in 
		red)
			echo "heartbeat" > /sys/class/leds/status-red/trigger
			echo "none" > /sys/class/leds/status-green/trigger
			;;
			
		green)
			echo "none" > /sys/class/leds/status-red/trigger
			echo "heartbeat" > /sys/class/leds/status-green/trigger
			;;
			
		yellow)
			echo "heartbeat" > /sys/class/leds/status-red/trigger
			echo "heartbeat" > /sys/class/leds/status-green/trigger
			;;
	esac
fi

exit 0