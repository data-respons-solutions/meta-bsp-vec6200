require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

inherit fsl-u-boot-localversion ${@oe.utils.conditional('MACHINE','vec6200-factory','imx6_usb','',d)}

DEPENDS += "bc-native dtc-native"

SRCREV_FORMAT = "uboot_common_system"
SRCREV_uboot = "3c99166441bf3ea325af2da83cfe65430b49c066"
SRCREV_common = "5d2db1b490ab096b31c5d409a916e510c309c4ac"
SRCREV_system = "afd9103e7ccb94c00a2b840aa48feb6a500c2195"

SRC_URI = "git://git.denx.de/u-boot.git;name=uboot \
           git://git@github.com/data-respons-solutions/uboot-vec6200.git;branch=master;protocol=ssh;destsuffix=git/board/datarespons/vec6200;name=system \
           gitsm://git@github.com/data-respons-solutions/uboot-common.git;branch=master;protocol=ssh;destsuffix=git/board/datarespons/common;name=common \
           file://0001-vec6200-add-links.patch \
           file://0002-vec6200-add-dt-to-Makefile.patch \
           file://0003-vec6200-add-to-Kconfig.patch \
           "

LOCALVERSION = "+dr-0.1"

EXTRA_OEMAKE += 'V=0'

PV_append = "${LOCALVERSION}"

UBOOT_BINARY = "u-boot-ivt.img"
SPL_BINARY = "SPL"

RPROVIDES_${PN} = "u-boot"

# Create imx_usb loader configs for machine vec6200-factory
python () {
    if d.getVar('MACHINE', True) == 'vec6200-factory':
        bb.build.addtask('do_imx6_usb', 'do_install', 'do_compile', d)
}

do_install_append_vec6200-factory() {
	for f in ${B}/imx6_usb/*; do
		install -m 0644 ${f} ${D}/boot/;
	done
}

do_deploy_append_vec6200-factory() {
	for f in ${B}/imx6_usb/*; do
		install -m 0644 ${f} ${DEPLOYDIR}/;
	done
}

IMX6_USB_DIR = "${B}/imx6_usb"
IMX6_USB_RAW_VID = "0x15a2"
IMX6_USB_RAW_PID = "0x0061"
IMX6_USB_DTB = "${FACTORY_DEVICETREE}"
IMX6_USB_DTB_LOADADDR = "0x11000000"
IMX6_USB_ZIMAGE_LOADADDR = "0x12000000"
IMX6_USB_INITRD_LOADADDR = "0x12C00000"

FILES_${PN}_append_vec6200-factory += "/boot"

UBOOT_CONFIG = "production"
