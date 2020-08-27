require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

IS_FACTORY = "0"
IS_FACTORY_factory = "1"
inherit fsl-u-boot-localversion ${@oe.utils.conditional('IS_FACTORY','1','imx6_usb','',d)}

DEPENDS += "bc-native dtc-native"

SRCREV_FORMAT = "uboot_common_system"
SRCREV_uboot = "36fec02b1f90b92cf51ec531564f9284eae27ab4"
SRCREV_common = "1a562e735f4cb5260e75e2f477cc683f9d9fb152"
SRCREV_system = "5462aa67f0f206c2388d66f8de41c818eafdc111"

SRC_URI = "git://git.denx.de/u-boot.git;name=uboot \
           git://git@github.com/data-respons-solutions/uboot-vec6200.git;branch=master;protocol=ssh;destsuffix=git/board/datarespons/vec6200;name=system \
           gitsm://git@github.com/data-respons-solutions/uboot-common.git;branch=master;protocol=ssh;destsuffix=git/board/datarespons/common;name=common \
           file://0001-vec6200-add-links.patch \
           file://0002-vec6200-add-dt-to-Makefile.patch \
           file://0003-vec6200-add-to-Kconfig.patch \
           "

LOCALVERSION = "+dr-0.4"

EXTRA_OEMAKE += 'V=0'

PV_append = "${LOCALVERSION}"

UBOOT_BINARY = "u-boot-ivt.img"
SPL_BINARY = "SPL"

RPROVIDES_${PN} = "u-boot"

# Create imx_usb loader configs for factory machine
python () {
    if d.getVar('IS_FACTORY', True) == '1':
        bb.build.addtask('do_imx6_usb', 'do_install', 'do_compile', d)
}

do_install_append_factory() {
	for f in ${B}/imx6_usb/*; do
		install -m 0644 ${f} ${D}/boot/;
	done
}

do_deploy_append_factory() {
	for f in ${B}/imx6_usb/*; do
		install -m 0644 ${f} ${DEPLOYDIR}/;
	done
}
IMX6_USB_DIR = "${B}/imx6_usb"
IMX6_USB_RAW_VID = "0x15a2"
IMX6_USB_RAW_PID = "0x0061"
IMX6_USB_PID = "0x2110"
IMX6_USB_DTB = "${FACTORY_DEVICETREE}"
IMX6_USB_DTB_LOADADDR = "0x11000000"
IMX6_USB_ZIMAGE_LOADADDR = "0x12000000"
IMX6_USB_INITRD_LOADADDR = "0x12C00000"


### TEMPORARY WORKAROUND FOR REV A
inherit fsl-u-boot-localversion ${@oe.utils.conditional('IS_FACTORY','0','imx6_usb','',d)}

IMX6_USB_DTB_vec6200 = "vec6200-q-revB.dtb"
IMX6_USB_INITRD_vec6200 = "datarespons-image-initramfs-${MACHINE}.${INITRAMFS_FSTYPES}"

python () {
    if d.getVar('IS_FACTORY', True) == '0':
        bb.build.addtask('do_imx6_usb', 'do_install', 'do_compile', d)
}

do_install_append_vec6200() {
	for f in ${B}/imx6_usb/*; do
		install -m 0644 ${f} ${D}/boot/;
	done
}

do_deploy_append_vec6200() {
	for f in ${B}/imx6_usb/*; do
		install -m 0644 ${f} ${DEPLOYDIR}/;
	done
}
### END OF WORKAROUND

FILES_${PN}_append_factory += "/boot"

UBOOT_CONFIG = "production"
