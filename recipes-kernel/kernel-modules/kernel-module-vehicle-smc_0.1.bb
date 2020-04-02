SUMMARY = "Vehicle smc system controller driver"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit module

BRANCH ?= "master"
SRCREV ?= "a892b7a0b3a10a47380603a0e17d4e2d9f903a02"
SRC_URI = "git://git@github.com/data-respons-solutions/kernel-module-vehicle-smc.git;protocol=ssh;branch=${BRANCH}"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "vec6200"
KERNEL_MODULE_AUTOLOAD += "smc"

RPROVIDES_${PN} += "kernel-module-vehicle-smc"
