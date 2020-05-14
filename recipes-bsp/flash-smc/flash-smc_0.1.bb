SUMMARY = "SMC flash utility"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://flash-smc.py"

RDEPENDS_${PN} += "stm32flash python3-core"

do_install() {
    install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/flash-smc.py ${D}${sbindir}/flash-smc
}
    	
PACKAGE_ARCH = "${MACHINE_ARCH}"
