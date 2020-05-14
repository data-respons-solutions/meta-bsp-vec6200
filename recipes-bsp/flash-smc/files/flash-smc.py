#!/usr/bin/env python3

import subprocess, argparse
from time import sleep
from subprocess import Popen, PIPE, CalledProcessError

def set_pin(fp, val):
    with open(fp, "w") as f:
        f.write(str(val))
        

def update_needed(existing, replace):
    [ex_major, ex_minor] = existing.split('.')
    [rep_major, rep_minor] = replace.split('.')
    exv = int(ex_major) * 256 + int(ex_minor)
    repv = int(rep_major) * 256 + int(rep_minor)
    return repv > exv


def execute(cmd):
    with Popen(cmd, stdout=PIPE, bufsize=1, universal_newlines=True) as p:
        for line in p.stdout:
            print(line, end='') # process line here

    if p.returncode != 0:
        raise CalledProcessError(p.returncode, p.args)
    else:
        return p

# Program start

parser = argparse.ArgumentParser()
parser.add_argument("--check", help="Do not commit", action="store_true")
parser.add_argument("--force", help="Force even if up to date", action="store_true")
parser.add_argument("--firmware", help="Path to firmware", required=True, action="store")
args = parser.parse_args()

boot_pin = "/sys/class/gpio/gpio113/value"
power_pin = "/sys/class/gpio/gpio159/value"
reset_pin = "/sys/class/gpio/gpio4/value"

if not (args.check or args.force):
    print("action --check or --force not selected")
    exit(1)

try:
    with open("/sys/bus/i2c/devices/2-0051/driver/unbind", "w") as u:
        u.write("2-0051")
except OSError as e:
    print("Could not unbind driver", e)
try:
    set_pin(power_pin, 1)
    sleep(0.1)
    set_pin(boot_pin, 1)
    sleep(0.1)
    set_pin(reset_pin, 1)
    # Apply reset
    sleep(1.5)
    set_pin(reset_pin, 0)
    sleep(0.1)
    set_pin(boot_pin, 0)
    # Out of reset in ROM code
    
    try:
        print("Reboot after flash ...")
        cmd = ["stm32flash", "-w", hexpath, "/dev/ttySMC"]
        res = execute(cmd)
        print("Reset SMC - goodbye ")
        cmd = ["/bin/sync"]
        execute(cmd)
        set_pin(reset_pin, 1)
        sleep(1.0)
        set_pin(reset_pin, 0)
    except CalledProcessError as e:
        print(e)
        retcode = e.returncode
        led_err()
        exit(1)
        
    # set_pin(power_pin, 0)
except OSError as e:
    print(e)
    led_err()
    exit(1)

exit(0)
