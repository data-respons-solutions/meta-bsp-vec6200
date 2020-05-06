DESCRIPTION = "udev rules for vec6200"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " file://10-uart.rules"

S = "${WORKDIR}"

do_install () {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/10-uart.rules ${D}${sysconfdir}/udev/rules.d/
}
