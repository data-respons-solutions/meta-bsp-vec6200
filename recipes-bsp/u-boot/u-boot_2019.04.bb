require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

inherit fsl-u-boot-localversion ${@oe.utils.conditional('MACHINE','vec6200-factory','imx6_usb','',d)}

DEPENDS += "bc-native dtc-native"

SRCREV_FORMAT = "uboot_common_system"
SRCREV_uboot = "3c99166441bf3ea325af2da83cfe65430b49c066"
SRCREV_common = "cec2bbf71e92e5b1dc7b6a9374971d7a683a4526"
SRCREV_system = "1b081d080c3ddef7bf17a5a87990dac54272223d"

SRC_URI = "git://git.denx.de/u-boot.git;name=uboot \
           gitsm://git@github.com/data-respons-solutions/uboot-common.git;branch=master;destsuffix=git/board/datarespons/common;name=common \
           git://git@github.com/data-respons-solutions/uboot-vec6200.git;branch=master;destsuffix=git/board/datarespons/vec6200;name=system \
           "

LOCALVERSION = "+dr-0.1"

EXTRA_OEMAKE += 'V=0'

PV_append = "${LOCALVERSION}"

UBOOT_BINARY = "u-boot-ivt.img"
SPL_BINARY = "SPL"

RPROVIDES_${PN} = "u-boot"

do_install_append() {
	install -d ${D}/boot
	install -m 644 ${B}/${config}/${UBOOT_BINARY}.log ${D}/boot/
	install -m 644 ${B}/${config}/${SPL_BINARY}.log ${D}/boot/
}

UBOOT_CONFIG = "production"
