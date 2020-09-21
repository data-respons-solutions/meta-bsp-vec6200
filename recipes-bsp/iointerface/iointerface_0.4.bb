DESCRIPTION = "vec6200 inteface for io"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += " \
	file://anctl.sh \
	file://dioctl.sh \
"

RDEPENDS_${PN} += "bc libiio-tests"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/anctl.sh ${D}${bindir}/anctl
    install -m 0755 ${WORKDIR}/dioctl.sh ${D}${bindir}/dioctl
}